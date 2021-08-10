-- 10)
-- Calcular todas as somas parciais de uma lista de inteiros.
-- Ex.: > somaParciais [1,2,3,4] -- [1,1+2,1+2+3,1+2+3+4]
-- [1, 3, 6, 10]

somaParciais :: [Int] -> [Int]
somaParciais [] = []
somaParciais [x] = [x]
somaParciais (x:y:l) = [x] ++ somaParciais ((x+y):l)