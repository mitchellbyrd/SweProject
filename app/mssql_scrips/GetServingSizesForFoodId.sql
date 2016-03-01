if objectproperty(object_id('dbo.GetServingSizesForFoodId'), N'IsProcedure') = 1
	drop procedure dbo.GetServingSizesForFoodId
go

create procedure dbo.GetServingSizesForFoodId
	@foodId int
as 
	set nocount on;

	select 
		ss.*
	from 
		dbo.ServingSizes ss
		inner join Foods f
			on ss.FoodId = f.Id
	where 
		f.Id = @foodId
go