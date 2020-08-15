cd ..
./gradlew build
docker build -t labforward/helloworldapp .
docker run -d -p 127.0.0.1:8080:8080/tcp labforward/helloworldapp:latest


