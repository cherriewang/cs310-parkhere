package main

import (
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
)

func check(err error) {
	if err != nil {
		panic(err)
	}
}

func ConnectToPG() *gorm.DB {
	db, err := gorm.Open("postgres", "host=postgres user=irfan dbname=parkhere sslmode=disable password=password")
	check(err)
	return db
}

func SetupDB(db *gorm.DB) {
	db.AutoMigrate(&User{}, &Listing{}, &Booking{}, &Review{})
}
