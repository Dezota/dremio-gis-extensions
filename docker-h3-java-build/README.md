# Custom Build of Uber's H3-Java Extensions for UBI8

The Maven Repo version of this requires version 2.29 of libm and libc.
However, Redhat's UBI8 image only has version 2.28.  Using the library 
will produce the following error `lib64/libm.so.6: version 'GLIBC_2.29' not
found.`

Run ```build.sh``` to use Docker to compile a version that is compatible with UBI8.

