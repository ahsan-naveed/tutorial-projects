package main

import (
	"fmt"
	"sort"
)

func mostActive(customers []string) []string {
	n := len(customers)
	tradesCount := make(map[string]int)
	for i := 0; i < n; i++ {
		if _, ok := tradesCount[customers[i]]; !ok {
			tradesCount[customers[i]] = 1
		} else {
			tradesCount[customers[i]] += 1
		}
	}

	var activeCustomers []string
	for customer, count := range tradesCount {
		percent := (float64(count) / float64(n)) * 100
		if percent >= 5.0 {
			activeCustomers = append(activeCustomers, customer)
		}
	}

	sort.Strings(activeCustomers)
	return activeCustomers
}

func main() {
	customers := []string{"Omega", "Alpha", "Omega", "Alpha", "Omega", "Alpha", "Omega", "Alpha", "Omega", "Alpha", "Omega", "Alpha", "Omega", "Alpha", "Omega", "Alpha", "Omega", "Alpha", "Omega", "Beta"}
	fmt.Println(mostActive(customers))
}

// input <- list of customers
// output -> list of active customers (active == made at least 5% of the total trades)
