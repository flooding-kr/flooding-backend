#!/bin/bash

aws ssm get-parameter --name "/flooding/prod/env" --with-decryption --query "Parameter.Value" --output text > /home/ubuntu/.env