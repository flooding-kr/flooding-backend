#!/bin/bash

aws ssm get-parameter --name "/flooding/prod/env" --with-decryption --query "Parameter.Value" --output text > /home/ubuntu/.env

aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 588738598492.dkr.ecr.ap-northeast-2.amazonaws.com

docker pull 588738598492.dkr.ecr.ap-northeast-2.amazonaws.com/flooding-ecr:latest