package com.example.trail.Classes;
//月份事务
public class MonthEvent {

    private class DayEventNode{//每日事务

        private class EventNode {//事务节点

            public class Event {//事务
                private String eventDate;
                private String eventDetails;
                private String isEventDone;
            }

            private Event event;

            private EventNode nextEvent;
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
        private EventNode headEvent;
        private EventNode tempEvent;

        DayEventNode(){
            headEvent.event=null;
        }
        public void addDayEvent(){

        }

    }
    private DayEventNode[] monthEvents=new DayEventNode[31]; //每日事务组成每月事务，以数组形式存储



    public void main(){
        MonthEvent monthEvent = null;
        for(int i=0;i<31;i++){
            DayEventNode.EventNode tempNode;
            tempNode=monthEvent.monthEvents[i].headEvent;
        }
    }
}
