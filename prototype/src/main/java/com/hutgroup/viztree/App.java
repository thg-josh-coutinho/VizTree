package com.hutgroup.viztree;

import java.util.stream;

public class App 
{
    public static void main( String[] args )
    {

	Supplier<FlowGraphUpdate> updates = App::nextUpdate;
	FlowGraphListener gl = new FlowGraphListener(args);
	FlowGraph g = initFlowGraph(args, gl);

	updates
	    .stream()
	    .foreach((update) -> graph.update(update));
    }

    private static FlowGraphUpdate nextUpdate()
    {
	throw new UnsupportedOperationException("Unimplemented");
    }

    private static String[] getFlowGraphArgs(String []args)
    {
	throw new UnsupportedOperationException("Unimplemented");
    }
    private static String[] getListenerArgs(String []args)
    {
	throw new UnsupportedOperationException("Unimplemented");
    }
    private static String[] getUpdateStreamArgs(String []args)
    {
	throw new UnsupportedOperationException("Unimplemented");
    }

}
