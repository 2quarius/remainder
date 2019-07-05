package com.example.trail.Classes;
//月份事务
public class MonthEvent {

    public class DayEventNode{//每日事务

        public class EventNode {//事务节点

            public class Event {//事务
                public String eventDate;
                public String eventDetails;
                public String isEventDone;
            }

            public Event event;

            public EventNode nextEvent;
            public EventNode(Event event){
                this.event=event;
            }
            public EventNode(){
                this.event=null;
            }
            public void addEvent(Event event){  //在每日事务中添加事务
                EventNode eventNode= new EventNode(event);
                tempEvent=headEvent;
                while(tempEvent.nextEvent!=null){
                    tempEvent=tempEvent.nextEvent;
                }
                tempEvent.nextEvent=eventNode;
            }
        }
        public EventNode headEvent;
        public EventNode tempEvent;

        DayEventNode(){
            headEvent.event=null;
        }
        public void addDayEvent(){
        }

        public EventNode.Event getFirstEvent(){
            tempEvent=headEvent;
            if (tempEvent.nextEvent!=null){
                tempEvent=tempEvent.nextEvent;
                return tempEvent.event;
            }
            else return null;
        }
        public EventNode.Event getLastEvent(){
            tempEvent=headEvent;
            while(tempEvent.nextEvent!=null){
                tempEvent=tempEvent.nextEvent;
            }
            return tempEvent.event;
        }

    }
    //每日事务组成每月事务，以数组形式存储
    public DayEventNode[] monthEvents=new DayEventNode[31];

    //获取每日第一个事务
    public DayEventNode.EventNode.Event getDayFirstEvent(int day){
        return monthEvents[day].getFirstEvent();
    }

    //获取每日最后一个事务
    public DayEventNode.EventNode.Event getDayLastEvent(int day){
        return monthEvents[day].getLastEvent();
    }
}
