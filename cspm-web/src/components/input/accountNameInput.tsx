'use client'

import { useInputStore } from '@/components/Zustand/accountPageStore'

export default function AccountNameInput() {
  const { inputAccountName, setInputAccountName, resultAccountName } = useInputStore()
  const handleChangeAccountName = (e: any) => {
    setInputAccountName(e.target.value)
  }
  return (
    <>
      <input
        id='accountname-input'
        name='accountname-input'
        className='mx-2 h-[80%] w-[50%] px-2'
        value={inputAccountName}
        onChange={handleChangeAccountName}
        placeholder='내용 입력 후 조회'
      ></input>
      <span className='text-red'>
        {resultAccountName ? resultAccountName : '계열사 별 중복할 수 없습니다.'}
      </span>
    </>
  )
}
