-- 2)
-- Calcular o somatório dos elementos ímpares de uma lista de inteiros.
-- Ex.: > somaImpares [1,3,2,7,4,6,5] -- 16=1+3+7+5
-- 16

somaImpares :: [Integer] -> Integer
somaImpares [] = 0
somaImpares (x:y)
    | x `mod` 2 == 0 = somaImpares y
    | otherwise = x + somaImpares y
