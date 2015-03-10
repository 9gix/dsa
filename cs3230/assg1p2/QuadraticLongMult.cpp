// CS3230-PA1-ABC
// Multiplication of 2 n-digit integers.
// Naive O(n^2) multiplication algorithm (Long Multiplication)
// Name:
// Date:

#include <iostream>
#include <string>

using namespace std;

const string    Digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
const char      RADIX_POINT = '.';
const int       MAXSIZE = 10100;

inline int valueOf(char x){ // integer value of a digit
    if ('0' <= x and x <= '9') return x - '0';
    if ('A' <= x and x <= 'Z') return x - 'A' +10;
    return 255;
};

int A[MAXSIZE], B[MAXSIZE], *Temp, *Res;

//trim unnecessary zeros and radix point
string trim(string aStr){
    string X = aStr;
    //leading zeros:
    while(X.length()>1 and X[0] == '0'and X[1] != RADIX_POINT) X.erase(0,1); //000.001

    //trailing zeros and radix point:
    if (X.find(RADIX_POINT) != string::npos){
        while(X.length()>=1 and X[X.length()-1] =='0')
            X.erase(X.length()-1,1);//0.010; 1.000
        if   (X.length()>=1 and X[X.length()-1] == RADIX_POINT)
            X.erase(X.length()-1);//123.
        if (X[0] == RADIX_POINT)
            X = "0" + X; // insert "0" into ".123"
    };
    if (X == "") X = "0";
    return X;
};



// Convert string into array of integer:
// A[0] stores length of the number;
// Digits are stored in reverse order, example:
// X = "12.444"
// A = {5,4,4,4,2,1}, rpA = 3;
void convert2IntArr(string X, int *A, int &rpA){

    rpA = X.find(RADIX_POINT);
    if (rpA == string::npos) rpA = 0;
    else rpA = X.length()-1-rpA;

    if (rpA!=0) X.erase(X.find(RADIX_POINT),1);
    X = trim(X);

    A[0] = X.length();
    for (int i = 0; i < X.length(); i++){
        A[X.length()-i] = valueOf(X[i]);
    }
}

//Convert an array A to string:
string convertIntArr2Str(int *A, int rpA){
    while(A[0]<=rpA){
        A[0]++;
        A[A[0]] = 0;
    };
    string S = "";
    for (int i = 0; i < A[0]; i++){
        if (rpA and i == rpA ) S = RADIX_POINT + S;
        S = Digits[A[i+1]] + S;
    }
    return trim(S);
};

// Adding two arrays with offset: USEFUL for karatsuba algorithm!!
// A = A + B * base^offset
inline void add(int *A, int *B, int offset, int base){
    int lenA = A[0], lenB = B[0];
    //A.resize(MAXSIZE);
    int carry = 0;
    int i=1;
    offset++;

    int b;
    while(i<=lenB or carry>0){
        if (offset > lenA) A[offset] = 0;
        if (i > lenB) b = 0; else b = B[i];

        carry       = A[offset] + b + carry;
        A[offset]   = carry%base;
        carry       = carry>=base?1:0;

        i++;
        offset++;
    };

    offset--;
    while(offset>1 and A[offset] == 0) offset--;
    if (offset>lenA) A[0] = offset;
};


// Res = A * B;
int * karatsuba (int *x, int *y, int base){
    /*
    ff (x[0] < 2 && y[0] < 2){
        return x * y; // quadratic long mult
    }

    //splitting by halves:
    int r = max(x[0], y[0]) / 2;
    x1, x0 = split X at R;
    y1, y0 = split Y at R;

    int one[r], two[r]; 
    memcpy(x1, x, 3 * sizeof(int)); 
    memcpy(x0, &x[3], 3 * sizeof(int)); 

    //recursive calls: 3 times: 
    z0 = karatsuba(x0, y0);
    z2 = karatsuba(x1, y1);
    z1 = karatsuba(x0+x1, y0+y1);

    //adding and subtracting:
    result = z2 * base ^(2*R) + (Z1-Z2-Z0)*base^R + z0
//return:
    // return result;
    // return {};
    */
};

int main()
{
    int T;
    string V,M,P;
    int base;

    //vector<int> A,B;
    int rpA, rpB, rpC;

    cin>>T;

    for(int i = 1; i <= T; ++i){
        cin>>base;
        cin>>V>>M;

        convert2IntArr(V,A,rpA);
        convert2IntArr(M,B,rpB);

        //Res = mul(A,B,base);      // naive - slow
        Res = karatsuba(A,B,base); // direct multiply - faster

        P = convertIntArr2Str(Res,rpA+rpB);
        cout <<P<<endl;
    };

    return 0;
}
