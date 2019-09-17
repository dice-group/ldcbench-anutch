default: build

build:
	mvn clean install -U -DskipTests
	docker build -f Dockerfile -t dicegroup/anutch .
push-images:
	docker push dicegroup/anutch
	
