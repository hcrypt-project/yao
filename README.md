Introduction
============

The classic approach of secure function evaluation (SFE) also deals with encrypted programs but is often based on Yao’s Garbled Circuits. The concept of garbled circuits is an alternative to homomorphically encrypted function representations and is currently considered the most effective way to hide program functionality. In contrast to FHE encryted data, garbled circuits encrypt the function tables of boolean gates. The input values are used as row and column keys to decrypt the output value of the gates. Garbled circuits only support one pass directed, acyclic circuits.

The Java-based sample implementation uses AES to encrypt the function tables and supports delegation only, i.e. the untrusted party is not allowed to input it’s own data which the first party encrypts during an oblivious transfer protocol.


License
=======

The source code is published under a MIT license:

Copyright © 2011

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
