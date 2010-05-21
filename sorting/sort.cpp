
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

void test_sort(char *msg, void (*sort_fn)(int seq[], int n) ) {
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

    std::cout << "Ok." << std::endl;
}

int main() {
    test_sort("Insertion Sort", insertion_sort);
    return 0;
}
