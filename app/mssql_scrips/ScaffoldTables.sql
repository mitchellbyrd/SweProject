create table Foods (
  Id int identity(1,1) primary key
, Name varchar(200)
, LastConsumed varchar(32) );

create table ServingSizes (
  Id int identity(1,1) primary key
, ServingSizeType varchar(85)
, Amount float
, Calories float
, Proteins float
, Carbs float
, Fats float
, FoodId int foreign key references Foods(Id) );

create table Users (
  Id int identity(1,1) primary key
, EmailAddress varchar
, Password varchar
, NameFirst varchar
, NameLast varchar
, BirthDate varchar
, Weight float
, PreferedCalorieLimit float
, PreferedFatLimit float
, PreferedCarbLimit float
, PreferedProteinLimit float );

create table Days (
  Id int identity(1,1) primary key
, UserId int foreign key references Users(Id)
, Date varchar(32)
, CurrentTotalCalorie float
, CurrentTotalFat float
, CurrentTotalCarb float
, CurrentTotalProtein float
, GoalTotalCalorie float
, GoalTotalFat float
, GoalTotalCarb float
, GoalTotalProtein float );

create table Food_Day (
  Id int identity(1,1) primary key
, ServingSizesId int foreign key references ServingSizes(Id)
, DayId int foreign key references Days(Id)
, NumberOfServings float );