-- 4)
-- Verificar se um número é primo.
-- Ex.: > primo 17
-- True
-- > primo 0
-- False

primo :: Integer -> Bool
primo x 
 | x == 1 = False
 | otherwise = primoQ x (x - 1)

primoQ :: Integer -> Integer -> Bool
primoQ x y
 | y == 1 = True
 | x `mod` y == 0 = False
 | otherwise = primoQ x (y - 1)
