import math
import cv2

def output_keypoints(image_path, proto_file, weights_file, threshold, model_name, BODY_PARTS):
    global points

    # 이미지 읽어오기
    frame = cv2.imread(image_path)

    # 네트워크 불러오기
    net = cv2.dnn.readNetFromCaffe(proto_file, weights_file)

    # 입력 이미지의 사이즈 정의
    image_height = 368
    image_width = 368

    # 네트워크에 넣기 위한 전처리
    input_blob = cv2.dnn.blobFromImage(frame, 1.0 / 255, (image_width, image_height), (0, 0, 0), swapRB=False,
                                       crop=False)

    # 전처리된 blob 네트워크에 입력
    net.setInput(input_blob)

    # 결과 받아오기
    out = net.forward()
    # The output is a 4D matrix :
    # The first dimension being the image ID ( in case you pass more than one image to the network ).
    # The second dimension indicates the index of a keypoint.
    # The model produces Confidence Maps and Part Affinity maps which are all concatenated.
    # For COCO model it consists of 57 parts – 18 keypoint confidence Maps + 1 background + 19*2 Part Affinity Maps. Similarly, for MPI, it produces 44 points.
    # We will be using only the first few points which correspond to Keypoints.
    # The third dimension is the height of the output map.
    out_height = out.shape[2]
    # The fourth dimension is the width of the output map.
    out_width = out.shape[3]

    # 원본 이미지의 높이, 너비를 받아오기
    frame_height, frame_width = frame.shape[:2]

    # 포인트 리스트 초기화
    points = []

    print(f"\n========== {model_name} ==========")
    for i in range(len(BODY_PARTS)):

        # 신체 부위의 confidence map
        prob_map = out[0, i, :, :]

        # 최소값, 최대값, 최소값 위치, 최대값 위치
        min_val, prob, min_loc, point = cv2.minMaxLoc(prob_map)

        # 원본 이미지에 맞게 포인트 위치 조정
        x = (frame_width * point[0]) / out_width
        x = int(x)
        y = (frame_height * point[1]) / out_height
        y = int(y)

        if prob > threshold:  # [pointed]
            if (i == 0 or i == 1 or i == 17 or i == 18):
                cv2.circle(frame, (x, y), 5, (0, 255, 255), thickness=-1, lineType=cv2.FILLED)
                cv2.putText(frame, str(i), (x, y), cv2.FONT_HERSHEY_SIMPLEX, 0.6, (0, 0, 255), 1, lineType=cv2.LINE_AA)

            points.append((x, y))
            # print(f"[pointed] {BODY_PARTS[i]} ({i}) => prob: {prob:.5f} / x: {x} / y: {y}")

        else:  # [not pointed]
            if (i == 0 or i == 1 or i == 17 or i == 18):
                cv2.circle(frame, (x, y), 5, (0, 255, 255), thickness=-1, lineType=cv2.FILLED)
                cv2.putText(frame, str(i), (x, y), cv2.FONT_HERSHEY_SIMPLEX, 0.6, (255, 0, 0), 1, lineType=cv2.LINE_AA)

            points.append(None)
            # print(f"[not pointed] {BODY_PARTS[i]} ({i}) => prob: {prob:.5f} / x: {x} / y: {y}")

    return frame


def output_keypoints_with_lines(POSE_PAIRS, frame):
    # 프레임 복사
    frame_line = frame.copy()


    # Neck 과 (LEar 또는 REar) 좌표값이 존재한다면 degree 측정
    if (points[1] is not None) and (points[18] is not None):
        calculate_degree(point_1=points[1], point_2=points[18], frame=frame_line)

    for pair in POSE_PAIRS:
        part_a = pair[0]  # 0 (Head)
        part_b = pair[1]  # 1 (Neck)
        if points[part_a] and points[part_b]:
            print(f"[linked] {part_a} {points[part_a]} <=> {part_b} {points[part_b]}")
            # Neck 과 Ear 이라면 분홍색 선
            if (part_a == 1 and part_b == 18) or (part_a == 1 and part_b == 17):
                cv2.line(frame, points[part_a], points[part_b], (255, 0, 255), 3)
            else:  # 노란색 선
                cv2.line(frame, points[part_a], points[part_b], (0, 255, 0), 3)
        else:
            print(f"[not linked] {part_a} {points[part_a]} <=> {part_b} {points[part_b]}")

    # 포인팅 되어있는 프레임과 라인까지 연결된 프레임을 가로로 연결
    frame_horizontal = cv2.hconcat([frame, frame_line])
    cv2.imshow("Output_Keypoints_With_Lines", frame_horizontal)
    cv2.waitKey(0)
    cv2.destroyAllWindows()


def calculate_degree(point_1, point_2, frame):
    # 역탄젠트 구하기
    dx = point_2[0] - point_1[0]
    dy = point_2[1] - point_1[1]
    rad = math.atan2(abs(dy), abs(dx))

    # radian 을 degree 로 변환
    deg = rad * 180 / math.pi

    # degree 가 80'보다 작으면 거북목으로 진단
    if deg < 80:
        string = "Turtle"
        cv2.putText(frame, string, (0, 25), cv2.FONT_HERSHEY_DUPLEX, 1, (255, 0, 255))
        print(f"[degree] {deg} ({string})")
    else:
        string = "Normal"
        cv2.putText(frame, string, (0, 25), cv2.FONT_HERSHEY_DUPLEX, 1, (255, 0, 255))
        print(f"[degree] {deg} ({string})")


# ====== main ========
BODY_PARTS_BODY_25 = {0: "Nose", 1: "Neck", 2: "RShoulder", 3: "RElbow", 4: "RWrist",
                      5: "LShoulder", 6: "LElbow", 7: "LWrist", 8: "MidHip", 9: "RHip",
                      10: "RKnee", 11: "RAnkle", 12: "LHip", 13: "LKnee", 14: "LAnkle",
                      15: "REye", 16: "LEye", 17: "REar", 18: "LEar", 19: "LBigToe",
                      20: "LSmallToe", 21: "LHeel", 22: "RBigToe", 23: "RSmallToe", 24: "RHeel", 25: "Background"}