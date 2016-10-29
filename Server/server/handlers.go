package main

import (
	//"encoding/json"
	"net/http"
)

func GetListings(w http.ResponseWriter, r *http.Request) {
	//listings := GetListingsDB(db)
	//listingsJSON := Listings{
	//	Listings: listings,
	//}
	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
	if origin := r.Header.Get("Origin"); origin != "" {
		w.Header().Set("Access-Control-Allow-Origin", origin)
		w.Header().Set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE")
		w.Header().Set("Access-Control-Allow-Headers",
			"Accept, Content-Type, Content-Length, Accept-Encoding, X-CSRF-Token, Authorization")
	}

	// Stop here if its Preflighted OPTIONS request
	if r.Method == "OPTIONS" {
		return
	}

	w.WriteHeader(http.StatusOK)
	/*if err := json.NewEncoder(w).Encode(json); err != nil {
		panic(err)
	}*/
}
