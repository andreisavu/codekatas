
#include <iostream>
#include <assert.h>

void swap(int &a, int &b) {
    int aux = a;
    a = b;
    b = aux;
}

void insertion_sort(int seq[], int n) {
    for(int i=0; i<n-1; i++) {
        int j = i + 1;
        while(j != 0 && seq[j] < seq[j-1]) {
            swap(seq[j], seq[j-1]);
            j--;
        }
    }
}   

int check(int seq[], int n) {
    for(int i=1; i<n; i++) {
        if(seq[i] < seq[i-1]) {
            return 0;
        }
    }
    return 1;
}

int main() {

    int reverse_order[] = {4,3,2,1};
    int n = 4;

    insertion_sort(reverse_order, n);
    assert(check(reverse_order, n));

    int empty_sequence[] = {};
    
    insertion_sort(empty_sequence, 0);
    assert(check(empty_sequence, 0));

    return 0;
}

