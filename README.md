# vlcjideo
Spring boot application with JavaFX and vlcj to display RTSP videos
# How to run
* execute run.bat 
* or execute run.sh

# How to change video URL
```
curl -X POST -H 'Content-Type: application/json' -i http://localhost:1971/api/v1/video/set --data '{
  "url":"https://www.youtube.com/watch?v=rtC84CMVILw"
}'
```
# Reference
* [SpringBoot and JavaFx cookbook](https://medium.com/@alenibric/springboot-and-javafx-cookbook-e8f3dd80deb9)

# Some RTSP URL for testing 
* rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov
* rtsp://freja.hiof.no:1935/rtplive/_definst_/hessdalen03.stream
* rtsp://170.93.143.139/rtplive/470011e600ef003a004ee33696235daa