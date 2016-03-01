if objectproperty(object_id('dbo.SearchFoodNameLike'), N'IsProcedure') = 1
	drop procedure dbo.SearchFoodNameLike
go

create procedure dbo.SearchFoodNameLike
	@foodName varchar(300)
as 
	set nocount on

	select
		f.Name
	from 
		Foods f
	where 
		f.Name like concat('%', @foodName, '%')
go
