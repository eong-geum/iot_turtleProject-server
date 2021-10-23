import base64
from http import server
import cv2
import requests
import json
import datetime
from requests.api import head


# 이미지 읽어오기
def loadImage(file_path):
    src = cv2.imread(file_path, cv2.IMREAD_COLOR)
    # 이미지 인코딩
    retval, buf = cv2.imencode('.jpg', src)
    # base64 로 text로 인코딩
    jpg_as_text = bytes.decode(base64.b64encode(buf))
    return src, retval, buf, jpg_as_text


if __name__ == "__main__":
    id = "testid"
    name = "John Doe"
    image_path = "./turtleneck.jpeg"
    src, retval, buf, jpg_as_text = loadImage(image_path)
    # 현재 시간 파악.
    current_date = datetime.datetime.now()
    nowDate = current_date.strftime(
        '%Y-%m-%d')+' '+current_date.strftime('%H:%M:%S')

    # JSON 형식으로 서버에 전달
    headers = {'Content-Type': 'application/json; charset=utf-8'}
    res = requests.post("http://localhost:8080/data",
                        json={
                            'id': id,
                            'name': name,
                            'nowDate': nowDate,
                            'encodingContent': jpg_as_text
                        },
                        headers=headers)


# # 원본, 인코딩, 디코딩 이미지 화면 출력
# cv2.imshow('src', src)
# cv2.imshow('encoding', buf)
# cv2.imshow('decoding', original_image)

# 화면 출력창 대기/닫기
# cv2.waitKey()
# cv2.destroyAllWindows()
