#include <cuda_runtime.h>
#include <curand.h>
#include<curand_kernel.h>


extern "C"
__global__ void normal_double(int n,double *means,double std,double *result, curandGenerator_t s) {
        int tid = threadIdx.x + blockIdx.x * blockDim.x;
         for(int i = tid; i < n; i += blockDim.x*gridDim.x) {
                     double *curr = &result[i];
                     curandGenerateNormal(s,curr,1,means[i],std);
          }
}

