import base64
import cv2
import requests
import json
import asyncio
import socket
import sys
from select import select

from requests.api import head

# image file 
image='C:/Users/beomsic/Desktop/bg.jpeg' 

encode_image=""
# print(type(encode_image))
with open(image,'rb') as img:
    img_b64=base64.b64encode(img.read())
    encode_image=bytes.decode(img_b64)



data={"name":"client","encoingContent":encode_image}
headers={'Content-Type':'application/json; charset=utf-8'}
# post
# http://{LAN wi-fi IPv4 Address:{portNumber}}
res=requests.post("http://172.24.176.1:8080/photo",json={'name':'client','encodingContent':encode_image},headers=headers)


# # 원본, 인코딩, 디코딩 이미지 화면 출력
# cv2.imshow('src', src)
# cv2.imshow('encoding', buf)
# cv2.imshow('decoding', webp_img)

# # 화면 출력창 대기/닫기
# cv2.waitKey()
# cv2.destroyAllWindows()

