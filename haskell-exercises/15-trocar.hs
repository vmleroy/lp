-- 15)
-- Desenvolver uma solução para um quiosque de saque eletrônico que, para um determinado  valor,
-- deve entregar o menor número de cédulas de R$1, R$5,  R$10, R$50 e R$100, da menor para a maior.
-- Ex.: > trocar 162
-- [1, 1, 10, 50, 100]

trocar :: Int -> [Int]
trocar 0 = []
trocar n = change n [100, 50, 10, 1] []

change :: Int -> [Int] -> [Int] -> [Int]
change 0 (x:y) l = l
change z (x:y) l
    | z > x  = change (z-x) (x:y) (x:l)
    | z == x = (x:l)
    | otherwise = change z y l