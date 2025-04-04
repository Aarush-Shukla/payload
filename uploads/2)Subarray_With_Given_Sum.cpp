#include<bits/stdc++.h>
using namespace std;


vector<int> subarraySum(vector<int>arr, int n, long long s)
    {
        int i=0;
        int j=0;
        long long sum=0;
        vector<int> ans;
        
        if(s==0){
            ans.push_back(-1);
            return ans;
        }
        
            while(j<n){
            
            sum+=arr[j];
            
            while(sum>s){  // if sum is more then we slide the window 
                sum-=arr[i];
                i++;
            }
            
            if(sum==s){
                ans.push_back(i+1);// 1 base indexing 
                ans.push_back(j+1);
                break;
            }
            j++;
        }
       if(!ans.empty()){
           return ans;
       }
      else{ 
          return {-1} ;
      }
    }