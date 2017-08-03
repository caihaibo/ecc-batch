package com.ecc.batch.dao;

import org.apache.ibatis.annotations.Param;

public interface SequenceMapper {

	Long getSequenceNextVal(@Param("seqName") String seqName);

}
