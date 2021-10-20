from flask import Flask, request, jsonify, Response
import photo_decoding
import json

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
    
    # print(data)

    # make image file
    photo_decoding.decode_image(data)

    response = Response(status=200)
    return response


if __name__ == "__main__":
    app.run()
