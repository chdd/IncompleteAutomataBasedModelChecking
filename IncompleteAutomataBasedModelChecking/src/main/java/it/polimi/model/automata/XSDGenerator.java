package it.polimi.model.automata;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

public class XSDGenerator extends SchemaOutputResolver{

	@Override
	public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
        File file = new File(suggestedFileName);
        StreamResult result = new StreamResult(file);
        result.setSystemId(file.toURI().toURL().toString());
        return result;
	}
}
