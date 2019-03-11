%include "io.asm"

section .bss
buf resb 10
n1 resd 1
n2 resd 1

section .text
global _start
_start:
  ; lis le premier entier
  mov eax, buf
  call readline
  call atoi
  mov [n1], eax

  ; lis le deuxième entier
  mov eax, buf
  call readline
  call atoi
  mov [n2], eax

  ; réordonne n1 > n2
  mov eax, [n1]
  mov ebx, [n2]
  cmp eax, ebx

  jge start ; saute si n1 > n2

  mov ecx, eax
  mov eax, ebx
  mov ebx, ecx

  mov [n1], eax
  mov [n2], ebx

start:
  mov eax, [n1]
  mov ebx, [n2]

check:
  mov edx, 0
  div ebx
  cmp edx, 0
  je end
  mov eax, ebx
  mov ebx, edx
  jmp check

end:
  mov eax, ebx
  call iprintLF
  mov eax, 1
  int 0x80
