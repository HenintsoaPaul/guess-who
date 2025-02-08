docker network create cloud-network --driver bridge

cd identity

docker compose up --build -d

cd ../crypto

docker compose up --build -d

