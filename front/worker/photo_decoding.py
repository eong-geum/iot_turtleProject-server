import base64
import requests
import json
import cv2

# decode 한 이미지 파일 띄우는 코드


def decode_image(data):

    # JSON data decode
    photo_decode = base64.b64decode(data)

    # 'image_copy.jpg' 파일로 사진 저장.
    with open('image_copy.jpg', 'wb') as f_output:
        f_output.write(photo_decode)
