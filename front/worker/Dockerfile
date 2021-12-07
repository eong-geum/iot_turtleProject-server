# syntax=docker/dockerfile:1

# base image 설정
FROM python:3.9
LABEL maintainer="Kangho-Noh"

# 이 줄 아래부터 컨테이너 내부에서 수행되는 명령어들의 pwd를 /app으로 변경
WORKDIR /app

# 컨테이너 내부에 추가적으로 깔아야하는 pip 파이썬 dependency들을 local에서 저장하여 이미지 내부에도 복사
COPY requirements.txt requirements.txt

# 이미지를 빌드할 때 수행되어 이미지 내부에 파이썬 패키지들을 미리 깔아 놓는다.
# -r은 --requirement로 파일을 통해 다운로드 받는다는 뜻
RUN pip3 install -r requirements.txt
RUN apt-get update
RUN apt-get -y install libgl1-mesa-glx
RUN apt-get install libglib2.0-0

# 그 외 소스코드를 포함한 다른 파일들을 이미지 안에 복사한다.
COPY . .

# 컨테이너를 실행시킬 때 입력할 명령어를 설정한다.
# -m은 mode로 라이브러리를 명령어 삼아 실행시킨다는 뜻
# --host=0.0.0.0의 의미는 외부에서 해당 컨테이너에 접근할 수 있도록 여는 것
CMD ["python3", "-m", "flask", "run", "--host=0.0.0.0", "--port=5000"]
