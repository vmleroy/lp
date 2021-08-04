-- 11)
-- Linearizar uma lista de listas de inteiros.
-- Ex.: > linearizar [ [1,2], [5], [0,4,2] ]
-- [1,2,5,0,4,2]

linearizar :: [[Int]] -> [Int]
linearizar [] = []
linearizar (x:l) = x ++ (linearizar l)