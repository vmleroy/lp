-- 1)
-- Defina uma função recursiva para o cálculo de potência de dois números inteiros, onde
-- o primeiro número é elevado ao segundo. Não se pode usar o operador de potência (^).
-- Ex.: > potencia 2 3 -- 8=2*2*2
-- 8

potencia :: Int -> Int -> Int
potencia _ 0 = 1
potencia x 1 = x
potencia x y = x * potencia y (x-1) 
