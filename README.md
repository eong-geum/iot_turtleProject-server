# 엉금엉금 - Turtle Project 🐢
`카메라 모듈을 이용해 사용자가 거북목인지 아닌지 체크하고 웹, 앱 어플리케이션을 통해 사용자에게 알림을 보내 주는 서비스입니다.`

![turtle](./image/turtle.png)


## Installation
1. Download zip file
2. Download [OpenPose](https://github.com/CMU-Perceptual-Computing-Lab/openpose#installation) zip file
3. Unpack the zip file and install the model from that file
``` 
cd openpose-master

for Linux 
./models/getModels.sh

for Windows
./models/getModels.bat

```
4. install opencv-python
``` 
pip install opencv-python
```


## OpenPose
<hr />
OpenPose has represented the first real-time multi-person system to jointly detect human body, hand, facial, and foot keypoints (total 135 keypoints) on single images.


## Raspberry pi 
<hr />
take a picture of the user with the Raspberry Pi camera module.

# Server
-  Encode the photo and deliver it to the server in JSON format.
- The server decodes the received image and analyzes the photo.
- If a turtle neck is detected in the photo analysis results, the result is sent to the client.



# Reference
### OpenPose
* https://github.com/CMU-Perceptual-Computing-Lab/openpose

### OpenPose를 이용해 이미지를 띄우고 분석하는 소스 참고
* https://hanryang1125.tistory.com/6

### 