FROM golang:1.7.1-alpine

COPY . $GOPATH/src/github.com/cherriewang/cs310-parkhere/Server

WORKDIR $GOPATH/src/github.com/cherriewang/cs310-parkhere/Server/server

RUN go build

ENTRYPOINT ["./server"]