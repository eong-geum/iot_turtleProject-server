from flask import Flask, request, jsonify, Response
import photo_decoding
import json
import opencv_main
import opencv_turtle
import os

app = Flask(__name__)


@app.route('/')
def hello_world():
    return 'Turtle'


@app.route('/opencv')
def getOpencv():
    return "Turtle"


@app.route('/opencv', methods=['POST'])
def opencv():
    # POST로 전달 받은 JSON 형식 data get
    data = request.get_json()

    # Datebase에 저장할 내용들
    #user_name = data['name']
    #now_Date = data['nowDate']
    #now_Time = data['nowTime']

    # make image file
    photo_decoding.decode_image(data['encodingContent'])

    # analysis image
    opencv_main.start_opencv()
    # 분석 결과
    # 거북목 : Turtle
    # 보통 : Normal
    is_Turtle = opencv_turtle.is_Turtle
    print("is_Turtle : ", is_Turtle)
    # 사진 사용 후 사진 삭제
    os.remove("image_copy.jpg")

    # result = {'id': "1", 'user_name': user_name, 'now_Date': now_Date,
    #         'now_Time': now_Time, 'is_turtle': is_Turtle}
    result = {'is_turtle': is_Turtle}
    # JSON 형태로 return
    response = app.response_class(
        response=json.dumps(result),
        status=200,
        mimetype='application/json'
    )
    return response


if __name__ == "__main__":
    app.run()
