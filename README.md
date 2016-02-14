# jbowling_calc

Основной класс BowlingFrameGame, для запуска консольной утилиты по подсчету очков используйте команду (находясь в директории [target/classes](https://github.com/1gnatov/jbowling_calc/tree/master/target/classes))

`java -cp . ConsoleIOBowlingFrameGame`

При выборе первого режима можно задать количество игроков и заполнять таблицу с очками по фрейму на игрока. 
При выборе второго режима можно сразу ввести список бросков вида "числоСбитыхКегель числоСбитыхКегель...", разделенных пробелом

[Тесты] (https://github.com/1gnatov/jbowling_calc/tree/master/src/test/java)  
[Покрытие кода тестами] (https://github.com/1gnatov/jbowling_calc/blob/master/coverage/index.html):

Логика:  
  >BowlingFrameGame	100% (1/1)	100% (14/14)	100% (91/91)  
  >BonusCounterFrame	100% (1/1)	100% (2/2)	100% (5/5)  
  >GameFrame	100% (1/1)	100% (6/6)	100% (18/18)  

Консольный интерфейс:  
  >ConsoleIOBowlingFrameGame	0% (0/1)	0% (0/3)	0% (0/66)  

Программа сразу писалась через TDD (в основном так было добавлено много фич), присутствовал небольшой рефакторинг, отправлена на кодревью =)
