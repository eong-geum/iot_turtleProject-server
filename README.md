# ์๊ธ์๊ธ - Turtle Project ๐ข
`์นด๋ฉ๋ผ ๋ชจ๋์ ์ด์ฉํด ์ฌ์ฉ์๊ฐ ๊ฑฐ๋ถ๋ชฉ์ธ์ง ์๋์ง ์ฒดํฌํ๊ณ  ์น, ์ฑ ์ดํ๋ฆฌ์ผ์ด์์ ํตํด ์ฌ์ฉ์์๊ฒ ์๋ฆผ์ ๋ณด๋ด ์ฃผ๋ ์๋น์ค์๋๋ค.`

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
OpenPose has represented the first real-time multi-person system to jointly detect human body, hand, facial, and foot keypoints (total 135 keypoints) on single images.


## Raspberry pi 
take a picture of the user with the Raspberry Pi camera module.

# Server
-  Encode the photo and deliver it to the server in JSON format.
- The server decodes the received image and analyzes the photo.
- If a turtle neck is detected in the photo analysis results, the result is sent to the client.



# Reference
### OpenPose
* https://github.com/CMU-Perceptual-Computing-Lab/openpose

### OpenPose๋ฅผ ์ด์ฉํด ์ด๋ฏธ์ง๋ฅผ ๋์ฐ๊ณ  ๋ถ์ํ๋ ์์ค ์ฐธ๊ณ 
* https://hanryang1125.tistory.com/6

### 