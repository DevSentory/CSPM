'use client'
import { useSelectType } from '@/components/Zustand/store'
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'

export default function RegionSelectBox() {
  const { regionSelected, setRegionSelected } = useSelectType()
  const handleRegionChange = (value: string) => {
    setRegionSelected(value)
  }
  return (
    <Select value={regionSelected} onValueChange={handleRegionChange}>
      <SelectTrigger className='h-full w-[30%]'>
        <SelectValue placeholder='리전 선택' />
      </SelectTrigger>
      <SelectContent>
        <SelectGroup>
          <SelectItem disabled value='us-east-1'>
            us-east-1
          </SelectItem>
          <SelectItem disabled value='us-east-2'>
            us-east-2
          </SelectItem>
          <SelectItem disabled value='us-west-1'>
            us-west-1
          </SelectItem>
          <SelectItem disabled value='us-west-2'>
            us-west-2
          </SelectItem>
          <SelectItem disabled value='sa-east-1'>
            sa-east-1
          </SelectItem>
          <SelectItem disabled value='ca-central-1'>
            ca-central-1
          </SelectItem>
          <SelectItem disabled value='eu-west-1'>
            eu-west-1
          </SelectItem>
          <SelectItem disabled value='eu-west-2'>
            eu-west-2
          </SelectItem>
          <SelectItem disabled value='eu-west-3'>
            eu-west-3
          </SelectItem>
          <SelectItem disabled value='eu-central-1'>
            eu-central-1
          </SelectItem>
          <SelectItem disabled value='eu-north-1'>
            eu-north-1
          </SelectItem>
          <SelectItem disabled value='me-south-1'>
            me-south-1
          </SelectItem>
          <SelectItem disabled value='me-central-1'>
            me-central-1
          </SelectItem>
          <SelectItem disabled value='af-south-1'>
            af-south-1
          </SelectItem>
          <SelectItem disabled value='ap-northeast-1'>
            ap-northeast-1
          </SelectItem>
          <SelectItem value='ap-northeast-2'>ap-northeast-2</SelectItem>
          <SelectItem disabled value='ap-northeast-3'>
            ap-northeast-3
          </SelectItem>
          <SelectItem disabled value='ap-southeast-1'>
            ap-southeast-1
          </SelectItem>
          <SelectItem disabled value='ap-southeast-2'>
            ap-southeast-2
          </SelectItem>
          <SelectItem disabled value='ap-south-1'>
            ap-south-1
          </SelectItem>
          <SelectItem disabled value='ap-southeast-3'>
            ap-southeast-3
          </SelectItem>
        </SelectGroup>
      </SelectContent>
    </Select>
  )
}
