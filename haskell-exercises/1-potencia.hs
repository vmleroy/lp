-- 1)
-- Defina uma função recursiva para o cálculo de potência de dois números inteiros, onde
-- o primeiro número é elevado ao segundo. Não se pode usar o operador de potência (^).
-- Ex.: > potencia 2 3 -- 8=2*2*2
-- 8

potencia :: Integer -> Integer -> Integer
potencia x y
 | y == 1 = x
 | y == 0 = 1
 | otherwise = x * potencia x (y - 1)
