import base64
import requests
import json

res=requests.get("http://172.24.176.1:8080/photo")
print(json.loads(res).get("name"));


# photo_file=open("photo.png","wb")
# photo_file.write(base64.b64encode(encodeImg))