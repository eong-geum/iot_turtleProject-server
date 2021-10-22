from flask import Flask, request, jsonify, Response
import photo_decoding
import json
import opencv_main
import opencv_turtle

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
    user_name=data['name']
    now_Date=data['nowDate']
    now_Time=data['nowTime']
    
    # make image file
    photo_decoding.decode_image(data['encodingContent'])

    # analysis image
    opencv_main.start_opencv()
    # 분석 결과 
    # 거북목 : Turtle
    # 보통 : Normal
    is_Turtle=opencv_turtle.is_Turtle

    response = Response(status=200)
    return response


if __name__ == "__main__":
    app.run()
