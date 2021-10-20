from flask import Flask, request, jsonify, Response
import json

app = Flask(__name__)


@app.route('/')
def hello_world():
    return 'Hello, World!'


@app.route('/opencv', methods=['POST'])
def opencv():
    data = request.get_json()
    print(data)
    response = Response(status=200)
    return response


if __name__ == "__main__":
    app.run()
