-- 5)
-- Verifique se um número é perfeito, isto é, é igual a soma de seus divisores (exceto o próprio número).
-- Ex.: > perfeito 28 -- 28=1+2+4+7+14
-- True

perfeito :: Integer -> Bool
perfeito x = igual x (somaDosDivisores x (x - 1))

somaDosDivisores :: Integer -> Integer -> Integer
somaDosDivisores x y
    | y == 1 = 1
    | x `mod` y == 0 = y + somaDosDivisores x (y - 1)
    | otherwise = somaDosDivisores x (y - 1)

igual :: Integer -> Integer -> Bool
igual x y
    | x == y = True
    | otherwise = False
