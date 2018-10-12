package com.enshub.uncompress;

import org.springframework.batch.item.ItemProcessor;

/**
 * Created by szty on 2018/10/10.
 */

public class FileProcessor implements ItemProcessor<People, People> {
    @Override
    public People process(People item) throws Exception {
        return needsToBeFiltered(item) ? null : item;
    }

    private boolean needsToBeFiltered(People item) {
        int age = item.getAge();
        return age > 30 ? true : false;
    }
}
