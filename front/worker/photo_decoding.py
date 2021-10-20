import base64
import requests
import json
import cv2

res = requests.get("http://localhost:8080/photo")
json_data = json.loads(res.content)

# api에서 가져온 JSON data decode
photo_decode = base64.b64decode(json_data["encodingContent"])

# 'test.jpg' 파일로 사진 저장.
with open('test.jpg', 'wb') as f_output:
    f_output.write(photo_decode)
