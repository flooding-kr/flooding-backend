#!/bin/bash

sudo docker run -d -p 8080:8080 --name flooding-server \
  --env-file /home/ubuntu/.env \
  588738598492.dkr.ecr.ap-northeast-2.amazonaws.com/flooding-ecr:latest