docker network create cloud_network

cd identity

docker compose up --build -d

cd ../crypto

docker compose up --build -d

