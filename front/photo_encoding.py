import base64
import cv2
import requests
import json

from requests.api import head


# # image file 
# image='C:/Users/beomsic/Desktop/bg.jpeg' 

# encode_image=""
# # print(type(encode_image))
# with open(image,'rb') as img:
#     img_b64=base64.b64encode(img.read())
#     encode_image=bytes.decode(img_b64)



# data={"name":"client","encoingContent":encode_image}
# headers={'Content-Type':'application/json; charset=utf-8'}
# post
# http://{LAN wi-fi IPv4 Address:{portNumber}}

# res=requests.post("http://172.10.8.60:8080/photo",json={'name':'client','encodingContent':encode_image},headers=headers)



# 이미지 읽어오기
def loadImage(file_path):
    src=cv2.imread(file_path,cv2.IMREAD_COLOR)
    # 이미지 인코딩
    retval, buf=cv2.imencode('.jpg',src)
    # base64 로 text로 인코딩    
    jpg_as_text=bytes.decode(base64.b64encode(buf))
    
    return src,retval,buf,jpg_as_text

src,retval,buf,jpg_as_text=loadImage('C:/Users/beomsic/Desktop/bg.jpeg')

# JSON 형식으로 서버에 전달
headers={'Content-Type':'application/json; charset=utf-8'}
res=requests.post("http://172.10.8.60:8080/photo",json={'name':'client','encodingContent':jpg_as_text},headers=headers)





# # 원본, 인코딩, 디코딩 이미지 화면 출력
# cv2.imshow('src', src)
# cv2.imshow('encoding', buf)
# cv2.imshow('decoding', original_image)

# 화면 출력창 대기/닫기
# cv2.waitKey()
# cv2.destroyAllWindows()

