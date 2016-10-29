package main

import (
	"fmt"
	"log"
	"net/http"
	"time"
)

func main() {

	router := NewRouter()
	time.Sleep(time.Second * 10)
	fmt.Println("starting to connect to DB...")
	db := ConnectToPG()
	fmt.Println("intermediate check")
	SetupDB(db)
	fmt.Println("connected")
	log.Fatal(http.ListenAndServe(":8080", router))
}
