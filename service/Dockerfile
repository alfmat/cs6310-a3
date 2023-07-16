# Compile our java files in this container
FROM gradle AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test --no-daemon

# Copy the jar and test scenarios into our final image
FROM openjdk
WORKDIR /usr/src/cs6310
COPY --from=builder /home/gradle/src/build/libs/*.jar ./drone_delivery.jar
ENTRYPOINT ["java", "-jar", "drone_delivery.jar"]
