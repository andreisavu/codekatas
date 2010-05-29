
#include <iostream>
#include <assert.h>
#include <stdlib.h>

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

void merge(int a[], int na, int b[], int nb, int r[]) {
    int i = 0, j = 0, k = 0;
    while(i<na && j<nb) {
        if(a[i] < b[j]) {
            r[k++] = a[i++];
        } else {
            r[k++] = b[j++];
        }
    }
    for(; i<na; i++) r[k++] = a[i];
    for(; j<nb; j++) r[k++] = b[j];
}

void merge_sort(int seq[], int n) {
    if( n <= 16 ) {
        insertion_sort(seq, n);
    } else {

        int midle = n / 2, i;
        int *left = new int[midle];
        int *right = new int[n-midle];

        for(i=0; i<midle; i++) {
            left[i] = seq[i];
        }
        for(; i<n; i++) {
            right[i-midle] = seq[i];
        }

        merge_sort(left, midle);
        merge_sort(right, n - midle);

        merge(left, midle, right, n - midle, seq);

        delete[] left;
        delete[] right;
    }
}

int partition(int seq[], int s, int e) {
    int d = 0;
    while(s < e) {
        if(seq[s] > seq[e]) {
            swap(seq[s], seq[e]);
            d = !d;
        }
        if(d) {
            s++;
        } else {
            e--;
        }
    }
    return s;
}

void quick_sort(int seq[], int s, int e) {
    if(s < e) {
        int k = partition(seq, s, e);
        quick_sort(seq, s, k);
        quick_sort(seq, k+1, e);
    }
}

void quick_sort(int seq[], int n) {
    quick_sort(seq, 0, n-1);
}

int check(int seq[], int n) {
    for(int i=1; i<n; i++) {
        if(seq[i] < seq[i-1]) {
            return 0;
        }
    }
    return 1;
}

void random_fill(int seq[], int n) {
    for(int i=0; i<n; i++) {
        seq[i] = random();
    }
}

void test_sort(std::string msg, void (*sort_fn)(int seq[], int n) ) {
    std::cout << "Testing: " << msg << " .... ";

    int reverse_order[] = {4,3,2,1};
    int random_order[] = {3,7,1,5};
    int n = 4;

    sort_fn(reverse_order, n);
    assert(check(reverse_order, n));

    sort_fn(random_order, n);
    assert(check(random_order, n));

    int empty_sequence[] = {};
    
    sort_fn(empty_sequence, 0);
    assert(check(empty_sequence, 0));

    int single_element[] = {1};
    sort_fn(single_element, 1);
    assert(check(single_element, 1));

    n = 50;
    int large_random[50];
    random_fill(large_random, n);

    sort_fn(large_random, n);
    assert(check(large_random, n));

    std::cout << "Ok." << std::endl;
}

int main() {
    test_sort("Insertion Sort", insertion_sort);
    test_sort("Merge Sort", merge_sort);
    test_sort("Quick Sort", quick_sort);
    return 0;
}
